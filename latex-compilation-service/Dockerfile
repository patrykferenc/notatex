# Ta wersja się builduje, ale potem zwraca empty responsy
# To-do: translate to english
ARG RUST_IMG
FROM ${RUST_IMG} AS build

RUN USER=root cargo new --bin latex-compiler-service
WORKDIR /latex-compiler-service

# Copy manifests
COPY ./Cargo.lock ./Cargo.lock
COPY ./projectfiles/Cargo.toml ./Cargo.toml
COPY ./projectfiles/Rocket.toml ./Rocket.toml

RUN cargo build --release
RUN rm ./src/*.rs

COPY ./projectfiles/src ./src

RUN rm ./target/release/deps/latex_compiler_service*
RUN cargo build --release

# deploy
FROM ${RUST_IMG}

COPY --from=build /latex-compiler-service/target/release/latex-compiler-service .
COPY --from=build /latex-compiler-service/Rocket.toml .

RUN mkdir /resources
COPY ./resources /resources

EXPOSE 8000

CMD [ "./latex-compiler-service" ]
