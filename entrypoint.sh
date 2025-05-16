#!/bin/sh
set -e

# Ajusta permissão no volume (modifique o caminho conforme necessário)
chown -R appuser:appgroup /var/lib/app/uploads

# Executa o comando como appuser
exec su-exec appuser "$@"