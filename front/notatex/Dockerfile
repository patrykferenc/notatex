ARG TARGET_PLATFORM
FROM node:alpine
WORKDIR /usr/app
COPY . .
RUN npm install
EXPOSE 8080
ENTRYPOINT ["npm", "start"]
