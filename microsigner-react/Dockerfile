FROM ubuntu:20.04

RUN apt update; apt -y upgrade; apt -y autoclean; apt -y autoremove; apt-get -y install curl

RUN mkdir /home/react

#RUN cd /home/react/ \
#    && curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.3/install.sh | bash \
#    && source ~/.bashrc \
#    && nvm install v18.18.0 \
#    && apt install npm \
#    && npm update npm -g \
#    && sudo apt -y install vite

RUN curl -sL https://deb.nodesource.com/setup_18.x | bash -
RUN apt -y install nodejs

COPY src /home/react
COPY .eslintrc.cjs /home/react
COPY index.html /home/react
COPY package-lock.json /home/react
COPY package.json /home/react
COPY vite.config.js /home/react
