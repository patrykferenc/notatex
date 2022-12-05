mod latex_utilization;
#[allow(non_snake_case)]
#[allow(dead_code)]
mod model;
use std::fs::File;
use std::io::{BufReader, Read, Result};
fn main() -> Result<()> {
    let tex_file = File::open("input.txt")?;
    let input_file = model::TexFile::new("output.tex".to_string(), tex_file);

    let result = latex_utilization::create_and_compile(input_file)?;

    let mut input_file_reader = BufReader::new(result);
    let mut input_file_contents = String::new();

    input_file_reader.read_to_string(&mut input_file_contents)?;
    
    println!("{}", input_file_contents);

    Ok(())
}
