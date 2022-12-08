use crate::model::TexFile;
use std::fs::{File,remove_file};
use std::io::{BufReader, Error, Read, Write};
use std::path::Path;
use std::process::Command;

pub fn create_and_compile(input: TexFile) -> Result<File, Error> {
    create_local_file_from_file_pointer(input.body(), String::from(input.name()))?;

    run_tex_compiler(String::from(input.name()))?;

    match was_compilation_succesfull(&"output.pdf".to_string()) {
        true => Ok(File::open("output.pdf")?),
        false => Ok(File::open("output.log")?),
    }
}

fn create_local_file_from_file_pointer(
    file_pointer: &File,
    local_filename: String,
) -> Result<(), Error> {
    let mut file_to_be_saved = File::create(local_filename)?;
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
pub fn run_cleanup_routine() -> Result<(), Error> {
    let _ = remove_file("output.pdf");
    let _ = remove_file("output.aux");
    let _ = remove_file("output.log");

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

fn was_compilation_succesfull(output_pdf_path: &String) -> bool {
    return Path::new(output_pdf_path).exists();
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn given_existing_directory_asert_returns_true() {
        let result = was_compilation_succesfull(&"test_resources/test_file.tex".to_string());
        assert_eq!(true, result);
    }
    #[test]
    fn given_not_existing_directory_asert_returns_false() {
        let result =
            was_compilation_succesfull(&"test_resources/invalid_test_file.tex".to_string());
        assert_eq!(false, result);
    }
    #[test]
    fn given_valid_exec_assert_no_error() {
        let exec_name = "/bin/sh".to_string();
        let _result = run_external_executable(&exec_name, &"");
    }
    #[test]
    fn given_invalid_exec_panic() {
        let exec_name = "/bin/nosuchexeexists".to_string();
        let expected = Err(std::io::ErrorKind::NotFound);
        let result = run_external_executable(&exec_name, &"").map_err(|e| e.kind());
        assert_eq!(expected, result);
    }
    #[test]
    fn given_correct_data_create_local_files_without_errors() {
        let test_file_pointer = File::open("test_resources/test_file.tex").unwrap();
        let _result =
            create_local_file_from_file_pointer(&test_file_pointer, String::from("output.tex"))
                .unwrap();
        assert_eq!(Path::new("/tmp/output.tex").exists(), true);
    }
}
