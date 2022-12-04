DOCKER_COMPOSE_COM=docker compose
IMAGES=notatex-notatex-app
start:
	$(DOCKER_COMPOSE_COM) up
stop:
	$(DOCKER_COMPOSE_COM) down
clean:
	docker image rm $(IMAGES)