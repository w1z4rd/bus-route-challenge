#!/bin/bash
die ( ) {
    echo
    echo "$*"
    echo
    exit 1
}

if [ $# -ne 2 ]; then
  die "Usage: $0 {start|stop|block} DATA_FILE"
fi

set -o errexit

DATA_FILE=$2
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
RUN="java -jar $DIR/build/libs/bus-route-challenge-0.0.1-SNAPSHOT.jar"
NAME="bus-route-challenge"
PID_FILE=/tmp/${NAME}.pid
LOG_FILE=/tmp/${NAME}.log

checkDataFile() {
  if [ ! -f ${DATA_FILE} ]; then
    die "ERROR: The DATA_FILE does not exist"
  fi
  local DATA_FILE_TYPE=`file -ib ${DATA_FILE}`
  if [[ ${DATA_FILE_TYPE:0:11} != "text/plain;" ]]; then
    die "ERROR: The DATA_FILE type must be plain text"
  fi
}

start() {
    checkDataFile
    if [ -f ${PID_FILE} ]; then
        if kill -0 $(cat ${PID_FILE}); then
            die "ERROR: Service already running"
        else
            rm -f ${PID_FILE}
        fi
    fi
    local CMD="$RUN $DATA_FILE > $LOG_FILE 2>&1 & echo \$!"
    sh -c "$CMD" > ${PID_FILE}
}

stop() {
    if [ ! -f ${PID_FILE} ] || ! kill -0 $(cat ${PID_FILE}); then
        die "ERROR: Service not running"
    fi
    kill -15 $(cat ${PID_FILE}) && rm -f ${PID_FILE}
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
        die "Usage: $0 {start|stop|block} DATA_FILE"
esac

