# /bin/sh

. /opt/venv/bin/activate

export FLASK_APP=main

export FLASK_ENV=production

flask init-db

nohup uwsgi --ini ./flask-app.ini &> uwsgi.out &

nginx -g 'daemon off;'
