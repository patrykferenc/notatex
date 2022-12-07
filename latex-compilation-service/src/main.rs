#[macro_use] extern crate rocket;
#[allow(non_snake_case)]
#[allow(dead_code)]
mod latex_utilization;
mod model;
use std::fs::File;
use rocket::data::{Data,ToByteUnit};
use std::io::{Error, BufReader, Read};
fn handle_post_req() -> Result<String,Error> {
    let tex_file = File::open("/tmp/requested.tex")?;
    let input_file = model::TexFile::new("/tmp/output.tex".to_string(), tex_file);

    let resulting_pdf_or_error = latex_utilization::create_and_compile(input_file)?;

    let mut resulting_file_reader = BufReader::new(resulting_pdf_or_error);
    let mut resulting_file_contents = String::new();

    resulting_file_reader.read_to_string(&mut resulting_file_contents)?;
    

    Ok(resulting_file_contents)
}
#[post("/compile-tex", data = "<paste>")]
async fn upload(paste: Data<'_>) -> Result<String,Error> {
    paste.open(1.terabytes()).into_file("/tmp/requested.tex").await?;
    let res = handle_post_req().unwrap();
    Ok(res)
}


#[launch]
fn rocket() -> _ {
    rocket::build().mount("/", routes![upload])
}