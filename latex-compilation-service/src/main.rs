#[allow(non_snake_case)]
#[allow(dead_code)]
mod model;
mod latex_utilization;
use std::fs::File;
use std::io::Result;
fn main() -> Result<()>{

   let tex_file = File::open("input.txt")?; 
   let input_file = model::TexFile::new("output.tex".to_string(),tex_file);
   let a = input_file.filename_no_extension();
   let _result = latex_utilization::create_and_compile(input_file)?;
   println!("{}",a);
   Ok(())
}