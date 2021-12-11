FROM node:13.12.0-alpine

WORKDIR /app

COPY package.json ./
COPY package-lock.json ./

RUN npm install --silent

EXPOSE 4000

COPY . ./

CMD ["npm", "start"]
