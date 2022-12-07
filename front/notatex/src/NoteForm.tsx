import { FormEvent, useRef } from "react";
import { Button, Col, Form, Row, Stack } from "react-bootstrap"
import { Link } from "react-router-dom";
import { NoteData } from "./App";

type NoteFormProps = {
    onSubmit: (note: NoteData) => void
}

export function NoteForm({onSubmit}: NoteFormProps) {
    const titleRef = useRef<HTMLInputElement>(null);
    const markdownRef = useRef<HTMLTextAreaElement>(null);

    function handleSubmit(e: FormEvent) {
        e.preventDefault();

        onSubmit({
            title: titleRef.current!.value,
            markdown: markdownRef.current!.value,
        });
    }

    return (
      <Form onSubmit={handleSubmit}>
        <Stack gap={3}>
          <Row>
            <Col>
              <Form.Group controlId="title">
                <Form.Label>Title</Form.Label>
                <Form.Control required ref={titleRef} placeholder="Enter title" />
              </Form.Group>
            </Col>
          </Row>
          <Form.Group controlId="markdown">
            <Form.Label>Body</Form.Label>
            <Form.Control required as="textarea" ref={markdownRef} rows={15} />
          </Form.Group>
          <Stack direction="horizontal" gap={2} className="justify-content-end">
            <Button type="submit" variant="primary">Save</Button>
            <Link to="..">
            <Button type="button" variant="outline-secondary">Cancel</Button>
            </Link>
          </Stack>
        </Stack>
      </Form>
    );
}