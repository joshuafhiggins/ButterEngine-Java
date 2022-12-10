use std::collections::HashMap;
use std::fs::File;
use std::io::prelude::*;

fn parse_key_value_pair(string: &str) -> Vec<String> {
    if string.starts_with('"') && string.ends_with('"') {
        let items: Vec<String> = string[1..string.len() - 1].split("\" \"").map(|s| s.to_string()).collect();
        return items;
    }
    return Vec::new();
}

fn parse_entry(parent: &mut HashMap<String, String>, section: &Vec<String>) {
    let mut entries: HashMap<String, Vec<Vec<usize>>> = HashMap::new();

    let mut indent = 0;
    let mut current_section_name: Option<String> = None;
    for (i, line) in section.iter().enumerate() {
        if line == "{" {
            if indent == 0 {
                current_section_name = Some(section[i - 1].to_string());
                let start = i + 1;
            }
            indent += 1;
        } else if line == "}" {
            indent -= 1;
            if indent == 0 {
                let stop = i;
                if entries.contains_key(&current_section_name.unwrap()) {
                    let existing_value = entries.get_mut(&current_section_name.unwrap()).unwrap();
                    existing_value.push(vec![start, stop]);
                    entries.insert(current_section_name.unwrap(), existing_value);
                } else {
                    entries.insert(current_section_name.unwrap(), vec![vec![start, stop]]);
                }
            }
        } else {
            if i < section.len() - 1 {
                if section[i + 1] == "{" {
                    // do nothing
                } else if indent == 0 {
                    let pair = parse_key_value_pair(line);
                    if pair.len() > 0 {
                        parent.entry(pair[0].to_string()).or_insert(pair[1].to_string());
                    }
                }
            } else {
                if indent == 0 {
                    let pair = parse_key_value_pair(line);
                    if pair.len() > 0 {
                        parent.entry(pair[0].to_string()).or_insert(pair[1].to_string());
                    }
                }
            }
        }
    }

    for (entry, value) in entries.iter() {
        if value.len() > 1 {
            parent.entry(entry.to_string()).or_insert(Vec::new());
            for part in value {
                let mut sub_dict = HashMap::new();
                parse_entry(&mut sub_dict, &section[part[0]..part[1]]);
                parent.get_mut(entry).unwrap().push(sub_dict);
            }
        } else if value.len() == 1 {
            parent.entry(entry.to_string()).or_insert(HashMap::new());
parse_entry(parent.get_mut(entry).unwrap(), &section[value[0][0]..value[0][1]]);
}
}
}

fn convert_vmf_to_dict(filepath: &str) -> HashMap<String, String> {
let mut vmf: Vec<String> = Vec::new();
let mut file = File::open(filepath).expect("Could not open file");
let mut contents = String::new();
file.read_to_string(&mut contents).expect("Could not read file");
for line in contents.lines() {
    vmf.push(line.trim().to_string());
}

let mut parent = HashMap::new();
parse_entry(&mut parent, &vmf);

return parent;
}

fn convert_vmf_to_json(filepath: &str, is_pretty: bool) -> String {
let vmf_dict = convert_vmf_to_dict(filepath);
let mut json = serde_json::to_string(&vmf_dict).expect("Could not convert to JSON");

if is_pretty {
    json = serde_json::to_string_pretty(&vmf_dict).expect("Could not convert to pretty JSON");
}

return json;
}

fn convert_vmf_to_json_export(filepath_in: &str, filepath_out: &str, is_pretty: bool) {
let jvmf = convert_vmf_to_json(filepath_in, is_pretty);
let mut file = File::create(filepath_out).expect("Could not create file");
file.write_all(jvmf.as_bytes()).expect("Could not write to file");
}

fn main() {
let args: Vec<String> = std::env::args().collect();
if args.len() == 2 {
    println!("{}", convert_vmf_to_json(&args[1], false));
} else if args.len() > 2 {
    let mut pretty_print = false;
    if args.contains(&"-p".to_string()) || args.contains(&"-pretty".to_string()) || args.contains(&"--p".to_string()) || args.contains(&"--pretty".to_string()) {
        pretty_print = true;
    }

    if args.len() == 3 {
        if pretty_print {
            println!("{}", convert_vmf_to_json(&args[1], true));
        } else {
            convert_vmf_to_json_export(&args[1], &args[2], false);
            println!("Converted {} > {}", args[1], args[2]);
        }
    } else if args.len() == 4 {
        convert_vmf_to_json_export(&args[1], &args[2], pretty_print);
        println!("Converted {} > {}", args[1], args[2]);
    }
}
}
