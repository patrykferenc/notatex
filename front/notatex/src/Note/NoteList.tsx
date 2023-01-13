import { useEffect, useMemo, useState } from "react";
import { Button, Card, Col, Form, Row, Stack } from "react-bootstrap";
import { Link } from "react-router-dom";
import { Note } from "../App";
import styles from "./NoteList.module.css";
import axios, { AxiosRequestConfig } from "axios";

type SimpleNote = {
  id: string;
  title: string;
};

type NoteNotatex = {
  uuid: string;
  title: string;
};

type NotatexListProps = {
  notatexNotes: Note[];
};

type NoteListProps = {
  notes: Note[];
};

export function NoteList({ notatexNotes }: NotatexListProps) {
  const [title, setTitle] = useState("");
  const [noteList, setNoteList] = useState<NoteNotatex[]>([]);

  // const filterdNotes = useMemo(() => {
  //   return notes.filter((note) => {
  //     return (
  //       title === "" || note.title.toLowerCase().includes(title.toLowerCase())
  //     );
  //   });
  // }, [notes, title]);

  const filterdNotes = useMemo(() => {
    return notatexNotes.filter((note) => {
      return (
        title === "" || note.title.toLowerCase().includes(title.toLowerCase())
      );
    });
  }, [notatexNotes, title]);

  //localhost:5050/api/notes
  http: useEffect(() => {
    async function loadData() {
      const data = await (await fetch("localhost:5050/api/notes")).json();

      console.log(data);
      setNoteList(data);
    }

    loadData();
  }, []);

  return (
    <>
      <Row className="align-items-center mb-4">
        <Col>
          <h1>Notes</h1>
        </Col>
        <Col xs="auto">
          <Stack gap={2} direction="horizontal">
            <Link to="/new">
              <Button variant="primary" data-e2e="create button">
                Create
              </Button>
            </Link>
          </Stack>
        </Col>
      </Row>
      <Form>
        <Row className="my-4">
          <Col>
            <Form.Group controlId="title">
              <Form.Label>Title</Form.Label>
              <Form.Control
                type="text"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
              />
            </Form.Group>
          </Col>
        </Row>
      </Form>
      <Row xs={1} sm={2} lg={3} xl={4} className="g-3">
        {filterdNotes.map((note) => (
          <Col key={note.uuid}>
            <NoteCard id={note.uuid} title={note.title} />
          </Col>
        ))}
      </Row>
    </>
  );
}

function NoteCard({ id, title }: SimpleNote) {
  return (
    <Card
      as={Link}
      to={`/${id}`}
      className={`h-100 text-reset text-decoration-none ${styles.card}`}
    >
      <Card.Body>
        <Stack
          gap={2}
          className="align-items-center justify-content-center h-100"
        >
          <span className="fs-5">{title}</span>
        </Stack>
      </Card.Body>
    </Card>
  );
}
