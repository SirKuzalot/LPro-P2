#!/bin/bash

if [ $# -ne 1 ]; then
    echo "Usage: $0 <test_folder>"
    exit 1
fi

test_folder="$1"
output_folder="starterkit/tests/output/$test_folder"

if [ ! -d "$test_folder" ]; then
    echo "Error: $test_folder is not a directory."
    exit 1
fi

mkdir -p "$output_folder"

find "$test_folder" -maxdepth 1 -type f | sort | while read -r filepath; do
    testfile=$(basename "$filepath")
    output_file="$output_folder/${testfile}.out"
    first_line=$(head -n 1 "$filepath")
    # Only mark as expected fail if the first line starts with '/* fail'
    if [[ "$first_line" =~ ^/\*[\ ]*fails? ]]; then
        expected_emoji="❌"
        tail -n +2 "$filepath" > /tmp/tmp_testfile
        java L0int /tmp/tmp_testfile 2>&1 | tail -n +3 > "$output_file"
    else
        expected_emoji="✅"
        java L0int "$filepath" 2>&1 | tail -n +3 > "$output_file"
    fi

    if head -n 1 "$output_file" | grep -qE '\b\w*Error\b'; then
        output_emoji="❌"
    else
        output_emoji="✅"
    fi

    if [ "$output_emoji" = "$expected_emoji" ]; then
        echo "✅ Test $testfile passed"
    else
        echo "❌ Test $testfile not passed"
    fi

    if [[ "$first_line" =~ ^/\*[\ ]*fail ]]; then
        rm /tmp/tmp_testfile
    fi
done