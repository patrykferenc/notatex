use crate::model::TexFile;
use std::fs::File;
use std::io::{BufReader, Error, Read, Write};
use std::path::Path;
use std::process::Command;

// Change it so it returns File instead of String
pub fn create_and_compile(input: TexFile) -> Result<String, Error> {
    // create local from pointer
    
    create_local_file_from_file_pointer(input.body(), String::from(input.name()))?;
    // run tex compiler
    run_tex_compiler(String::from(input.name()))?;
    // check if ready for request or no stack trace from logs
    match was_compilation_succesfull(create_expected_output_path(input.filename_no_extension())) {
        false => print!("option 1"),
        true => print!("oprion 2"),
    }
    // check if pdf file exists if not then error
    Ok(String::from(input.name()))
    // return file pointer to be sent
}

fn create_local_file_from_file_pointer(
    file_pointer: &File,
    local_filename: String,
) -> Result<(), Error> {
    let mut file_to_be_created_path: String = "/tmp/".to_owned();

    file_to_be_created_path.push_str(local_filename.as_str());

    let mut file_to_be_saved = File::create(file_to_be_created_path)?;
    let mut input_file_reader = BufReader::new(file_pointer);
    let mut input_file_contents = String::new();

    input_file_reader.read_to_string(&mut input_file_contents)?;
    file_to_be_saved.write(input_file_contents.as_bytes())?;
    Ok(())
}

fn run_tex_compiler(output_filename: String) -> Result<(), Error> {
    run_external_executable(&"/usr/bin/pdflatex".to_string(), &output_filename)?;
    Ok(())
}

fn run_external_executable(exe: &String, arg: &str) -> Result<(), Error> {
    Command::new(exe).arg(arg).output()?;
    Ok(())
}
fn create_expected_output_path(filename: String) -> String {
    let mut file_to_be_created_path: String = "/tmp/".to_owned();
    file_to_be_created_path.push_str(&filename.as_str()); 
    file_to_be_created_path.push_str(".pdf");
    return file_to_be_created_path;
}

fn was_compilation_succesfull(output_pdf_path: String) -> bool {
    return Path::new(&output_pdf_path).exists();
}
