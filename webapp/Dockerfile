FROM node:latest
ENV PATH /app/node_modules/.bin:$PATH

# set working directory
WORKDIR /app

COPY ["package.json", "package-lock.json*", "./"]
COPY ./src /app/src
COPY ./public /app/public




# COPY only the necessary files for the project inside the Docker image
#COPY ./build /app/build
#COPY ./public /app/public
#COPY ./src /app/src
#COPY gulpfile.js /app/gulpfile.js
#COPY jsconfig.json /app/jsconfig.json
#COPY package.json /app/package.json
#COPY package-lock.json /app/package-lock.json


# add `/app/node_modules/.bin` to $PATH


#RUN ls -la /app

RUN npm install

# port in the Runtime VM
EXPOSE 50003

# start app
CMD ["npm", "start"]