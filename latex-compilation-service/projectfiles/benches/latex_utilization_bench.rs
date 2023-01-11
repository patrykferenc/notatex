
use std::fs::remove_file;
use std::io::Error;
use std::path::Path;
use std::process::Command;

pub fn run_cleanup_routine() -> Result<(), Error> {
    let _ = remove_file("output.pdf");
    let _ = remove_file("output.aux");
    let _ = remove_file("output.log");

    Ok(())
}

pub fn run_external_executable(exe: &String, arg: &str) -> Result<(), Error> {
    Command::new(exe).arg(arg).output()?;
    Ok(())
}
pub fn create_expected_output_path(filename: String) -> String {
    let mut file_to_be_created_path: String = "/tmp/".to_owned();
    file_to_be_created_path.push_str(&filename.as_str());
    file_to_be_created_path.push_str(".pdf");
    return file_to_be_created_path;
}

pub fn was_compilation_succesfull(output_pdf_path: &String) -> bool {
    return Path::new(output_pdf_path).exists();
}
