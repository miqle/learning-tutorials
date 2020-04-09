FROM nginx:1.17.9

USER root

COPY ./docker/static/* /usr/share/nginx/html/
COPY ./docker/conf/default.conf /etc/nginx/conf.d/default.conf

RUN apt-get update && apt-get -y install  python3.7 && \
    apt-get -y install python3.7-dev build-essential && \
    apt-get -y install python3-venv && \
    apt-get -y install gcc && \
    apt-get -y install procps && \
    python3.7 -m venv /opt/venv

RUN mkdir -p /app
WORKDIR /app
# copying code
COPY ./api /app/api
COPY ./main.py /app
COPY ./scripts/start.sh /app
RUN chmod 755 /app/start.sh
COPY ./scripts/flask-app.ini /app/flask-app.ini
COPY ./scripts/flask-app.service /etc/systemd/system/flask-app.service
COPY ./requirements.txt /app

RUN echo "Install Python Packages ..." && \
    . /opt/venv/bin/activate && \
    pip install uwsgi && \
    pip install -r /app/requirements.txt

EXPOSE 80
CMD . /app/start.sh

