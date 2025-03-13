#!/bin/bash

for file in $(ls datagen/templates/models/*.json); do
    output=$(cat $file | sed s/PLACEHOLDER/$1/)
    filename=$1_$(basename $file)
    echo $output > src/main/resources/assets/lilium/models/block/$filename
done

for file in $(ls datagen/templates/blockstates/*.json); do
    output=$(cat $file | sed s/PLACEHOLDER/$1/)
    filename=$1_$(basename $file)
    echo $output > src/main/resources/assets/lilium/blockstates/$filename

    if [ $filename = "$1_fence.json" -o $filename = "$1_button.json" ]; then
        echo '{ "parent": "lilium:block/PLACEHOLDER_inventory" }' | sed s/PLACEHOLDER/$(basename $filename .json)/ > src/main/resources/assets/lilium/models/item/$filename
    else
        echo '{ "parent": "lilium:block/PLACEHOLDER" }' | sed s/PLACEHOLDER/$(basename $filename .json)/ > src/main/resources/assets/lilium/models/item/$filename
    fi
done