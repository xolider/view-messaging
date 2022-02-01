FROM node:16

COPY backend/index.js /app/
COPY backend/package*.json /app/

WORKDIR /app

RUN npm install

EXPOSE 8081/tcp

CMD ["node", "index.js"]