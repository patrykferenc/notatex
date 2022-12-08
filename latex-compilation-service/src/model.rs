use std::fs::File;
pub struct TexFile {
    name: String,
    body: File,
}
impl TexFile {
    pub fn body(&self) -> &File {
        &self.body
    }
    pub fn name(&self) -> &String {
        &self.name
    }
    pub fn new(name: String, body: File) -> Self {
        return TexFile {
            name: name,
            body: body,
        };
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use std::io::{BufReader, Read};
    #[test]
    fn given_correct_data_assert_constructor_creates_struct() {
        //given
        let test_file_pointer = File::open("test_resources/test_file.tex").unwrap();
        let mut input_file_reader = BufReader::new(test_file_pointer);
        let mut input_file_contents = String::new();
        input_file_reader
            .read_to_string(&mut input_file_contents)
            .unwrap();

        //when
        let texfile: TexFile = TexFile::new(
            "normal_name.tex".to_string(),
            File::open("test_resources/test_file.tex").unwrap(),
        );
        let mut texfile_reader = BufReader::new(texfile.body());
        let mut pointer_contents = String::new();
        texfile_reader
            .read_to_string(&mut pointer_contents)
            .unwrap();

        //then
        assert_eq!(texfile.name(), &"normal_name.tex".to_string());
        assert_eq!(pointer_contents, input_file_contents);
    }
}
