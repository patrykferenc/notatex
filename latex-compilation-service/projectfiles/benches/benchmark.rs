mod latex_utilization_bench;
use criterion::{
    black_box,
    criterion_group,
    criterion_main,
    Criterion
};
fn run_cleanup_routine(c: &mut Criterion){
    c.bench_function(
        "run_cleanup_routine",
        |b| b.iter(||latex_utilization_bench::run_cleanup_routine())
    );

}
fn run_external_executable(c: &mut Criterion){
    let argument = black_box("hello");
    c.bench_function(
        "run_cleanup_routine",
        |b| b.iter(||latex_utilization_bench::run_external_executable(&String::from("echo"),argument))
    );

}
fn run_create_expected_output_path(c: &mut Criterion){
    c.bench_function(
        "run_cleanup_routine",
        |b| b.iter(||latex_utilization_bench::create_expected_output_path(String::from("/tmp/file")))
    );

}
fn run_was_compilation_succesfull(c: &mut Criterion){
    c.bench_function(
        "run_cleanup_routine",
        |b| b.iter(||latex_utilization_bench::was_compilation_succesfull(&String::from("/tmp/test.db")))
    );

}

criterion_group!(benches,run_cleanup_routine,run_external_executable,run_create_expected_output_path,run_was_compilation_succesfull);
criterion_main!(benches);
