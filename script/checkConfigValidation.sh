##!/bin/bash
# 배열 초기화
configs=()

# 상위 경로로 이동
cd ..
homeDir=$(pwd)

# find 명령어를 사용하여 파일 경로를 읽고 배열에 저장
while IFS= read -r line; do
  configs+=("$line")
done < <(find "$homeDir" -name 'application*yml' -type f)

# yml 파일 별로 ddl-auto : create 존재 여부 파악
isDeployable=true
error=()

for config in "${configs[@]}"; do
  value=$(grep 'ddl-auto' "$config" | grep 'create')

  if [ "$value" != "" ]; then
    error+=("$config")
    error+=("$value")
    isDeployable=false
  fi
done

if [ "$isDeployable" = false ]; then
  echo "Validation failed: check ddl-auto"
  for element in "${error[@]}"; do
    echo "$element"
  done
  exit 1
else
  echo "Validation passed: All configurations are deployable."
fi


