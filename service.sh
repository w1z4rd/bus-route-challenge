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
PID_FILE=/tmp/${NAME}.pid
LOG_FILE=/tmp/${NAME}.log

checkDataFile() {
  if [ ! -f ${DATA_FILE} ]; then
    echo "The DATA_FILE does not exist"
    return 1
  fi
  local DATA_FILE_TYPE=`file -ib ${DATA_FILE}`
  if [[ ${DATA_FILE_TYPE:0:11} != "text/plain;" ]]; then
    echo "The DATA_FILE type must be plain text"
    return 1
  fi
}

start() {
    checkDataFile
    if [ -f ${PID_FILE} ]; then
        if kill -0 $(cat ${PID_FILE}); then
            echo "Service already running"
            return 1
        else
            rm -f ${PID_FILE}
        fi
    fi
    local CMD="$RUN $DATA_FILE > $LOG_FILE 2>&1 & echo \$!"
    sh -c "$CMD" > ${PID_FILE}
}

stop() {
    if [ ! -f ${PID_FILE} ] || ! kill -0 $(cat ${PID_FILE}); then
        echo "Service not running"
        return 1
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
        echo "Usage: $0 {start|stop|block} DATA_FILE"
esac

