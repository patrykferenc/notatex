import { Button, Col, Row, Stack } from "react-bootstrap";
import { ReactMarkdown } from "react-markdown/lib/react-markdown";
import { Navigate, useNavigate } from "react-router-dom";
import { useNote } from "./NoteLayout";
import { HashLink as Link } from "react-router-hash-link";
import React from "react";
import axios from "axios";

type NoteProps = {
  onDelete: (id: string) => void;
};

const client = axios.create({
  baseURL: "http://localhost:5050/api/notes/",
});

export function Note({ onDelete }: NoteProps) {
  const note = useNote();
  const navigate = useNavigate();

  // React.useEffect(() => {
  //   async function getPost() {
  //     const response = await client.get("/1");
  //   }
  //   getPost();
  // }, []);

  const deletePost = () => {
    client
      .delete("6d3518ed-eac2-46ef-be80-9033350d5257")
      .then(() => {
        alert("Post deleted!");
      })
      .catch((err) => {
        console.error(err);
      });
  };

  return (
    <>
      <Row className="align-items-center mb-4">
        <Col>
          <h1>{note.title}</h1>
        </Col>
        <Col xs="auto">
          <Stack gap={2} direction="horizontal">
            <Button
              onClick={() => {
                window.open(
                  "blob:http://localhost:5050/8273dca7-9fc5-4e1a-8a6d-b5d7b9d9466a",
                  "_blank"
                );
              }}
              variant="primary"
            >
              Save
            </Button>
            <Link to={`/${note.uuid}/edit`}>
              <Button variant="primary" data-e2e="edit button">
                Edit
              </Button>
            </Link>
            <Button
              onClick={() => {
                deletePost();
                onDelete(note.uuid);
                navigate("/");
              }}
              variant="outline-danger"
              data-e2e="delete button"
            >
              Delete
            </Button>
            <Link to="/">
              <Button variant="outline-danger" data-e2e="back button">
                Back
              </Button>
            </Link>
          </Stack>
        </Col>
      </Row>
      {/* <ReactMarkdown>{note.markdown}</ReactMarkdown> */}
    </>
  );
}

// 6d3518ed-eac2-46ef-be80-9033350d5257
