# FROM node:alpine
# RUN npm install react-scripts
# WORKDIR /app
# COPY . /app
# ENTRYPOINT [ "npm", "start" ]

FROM node:alpine
WORKDIR /usr/app
COPY . .
RUN npm install
ENTRYPOINT ["npm", "start"]