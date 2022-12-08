FROM ubuntu:latest
RUN apt-get update
# Get Rust
RUN curl https://sh.rustup.rs -sSf | bash -s -- -y

RUN echo 'source $HOME/.cargo/env' >> $HOME/.bashrc

RUN apt-get install texlive-latex-base -y
RUN mkdir -p /usr/src/myapp
WORKDIR /usr/src/myapp
COPY projectfiles .

RUN ~/.cargo/bin/cargo build --release
COPY ./resources /usr/src/myapp/target/release

CMD ["latex-compiler-service"]