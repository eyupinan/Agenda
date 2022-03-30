FROM node
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
# install and app dependencies
COPY ./schedule-app/package.json /app/package.json
RUN npm install
RUN npm install -g @angular/cli
# add app
COPY ./schedule-app /app
# start app
CMD ng serve --host 0.0.0.0 --proxy-config src/proxy.conf.json
EXPOSE 4200