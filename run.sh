#!/bin/bash
set -e

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo $DIR

tmux start-server

tmux new-session -d -s 'SCS'
tmux send-keys "cd $DIR/number-emitter && mvn spring-boot:run" C-m
tmux split-window -v -p 66
tmux send-keys "cd $DIR/number-enricher && mvn spring-boot:run" C-m
tmux split-window -v -p 50
tmux send-keys "cd $DIR/number-consumer && mvn spring-boot:run" C-m
tmux select-pane -t 0
tmux -2 attach-session -d

set +e
