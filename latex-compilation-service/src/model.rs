use std::fs::File;
pub struct TexFile {
    name : String,
    body : File,
}
impl TexFile {
    pub fn body(&self) -> &File {
        &self.body
    }
    pub fn name(&self) -> &String {
        &self.name
    }
    pub fn new(name: String, body: File) -> Self {
        return TexFile { name: name, body: body };
    }
    pub fn filename_no_extension(&self) -> String {
        let end_of_filename = self.name.find(".tex").unwrap();
        let a =  self.name.chars().take(end_of_filename).collect();
        return a;
    }
}