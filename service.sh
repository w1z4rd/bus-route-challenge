#!/bin/bash
if [ $# -ne 2 ]; then 
  echo "Usage: $0 {start|stop|block} DATA_FILE"
  exit 1
fi

set -o errexit

DATA_FILE=$2
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
RUN="java -jar $DIR/build/libs/bus-route-challenge-0.0.1-SNAPSHOT.jar"
NAME="bus-route-challenge"
PIDFILE=/tmp/$NAME.pid
LOGFILE=/tmp/$NAME.log

checkDataFile() {
  if [ ! -f $DATA_FILE ]; then
    echo "The DATA_FILE does not exist"
    return 1
  fi
  local DATA_FILE_TYPE=`file -ib $DATA_FILE`
  if [[ ${DATA_FILE_TYPE:0:11} != "text/plain;" ]]; then
    echo "The DATA_FILE type must be plain text"
    return 1
  fi
}

start() {
    checkDataFile
    if [ -f $PIDFILE ]; then
        if kill -0 $(cat $PIDFILE); then
            echo "Service already running"
            return 1
        else
            rm -f $PIDFILE
        fi
    fi
    local CMD="$RUN $DATA_FILE > $LOGFILE 2>&1 & echo \$!"
    sh -c "$CMD" > $PIDFILE
}

stop() {
    if [ ! -f $PIDFILE ] || ! kill -0 $(cat $PIDFILE); then
        echo "Service not running"
        return 1
    fi
    kill -15 $(cat $PIDFILE) && rm -f $PIDFILE
}

case $1 in
    start)
        start
        ;;
    stop)
        stop
        ;;
    block)
        start
        sleep infinity
        ;;
    *)
        echo "Usage: $0 {start|stop|block} DATA_FILE"
esac

