# Ta wersja się builduje, ale potem zwraca empty responsy
FROM rust AS build

RUN USER=root cargo new --bin latex-compiler-service
WORKDIR /latex-compiler-service

# Copy manifests
COPY ./projectfiles/Cargo.lock ./Cargo.lock
COPY ./projectfiles/Cargo.toml ./Cargo.toml

RUN cargo build --release
RUN rm ./src/*.rs

COPY ./projectfiles/src ./src

RUN rm ./target/release/deps/latex_compiler_service*
RUN cargo build --release

FROM rust

COPY --from=build /latex-compiler-service/target/release/latex-compiler-service .

CMD [ "./latex-compiler-service" ]
