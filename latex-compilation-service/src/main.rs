#[macro_use]
extern crate rocket;
#[allow(non_snake_case)]
#[allow(dead_code)]
mod latex_utilization;
mod model;
use rocket::data::{Data, ToByteUnit};
use std::fs::File;
use std::io::Error;
fn handle_post_req() -> Result<File, Error> {
    let tex_file = File::open("/tmp/requested.tex")?;
    let input_file = model::TexFile::new("/tmp/output.tex".to_string(), tex_file);

    let resulting_pdf_or_error = latex_utilization::create_and_compile(input_file)?;

    Ok(resulting_pdf_or_error)
}
#[post("/compile-tex", data = "<paste>")]
async fn upload(paste: Data<'_>) -> Result<File, Error> {
    latex_utilization::run_cleanup_routine()?;
    paste
        .open(1.terabytes())
        .into_file("/tmp/requested.tex")
        .await?;
    let result = handle_post_req().unwrap();
    Ok(result)
}

#[launch]
fn rocket() -> _ {
    rocket::build().mount("/", routes![upload])
}
