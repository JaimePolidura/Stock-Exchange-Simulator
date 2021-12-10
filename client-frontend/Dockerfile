FROM node:13.12.0-alpine

WORKDIR /app

COPY package.json ./
COPY package-lock.json ./

RUN npm install --silent
RUN npm install react-scripts@4.0.3 -g --silent

EXPOSE 3000

COPY . ./

CMD ["npm", "start"]
