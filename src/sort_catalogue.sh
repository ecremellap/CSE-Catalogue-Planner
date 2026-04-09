#!/bin/bash

# Script to sort catalogue by Class ID alphabetically and remove duplicates

INPUT_FILE="catalogue.txt"
OUTPUT_FILE="catalogue_az.txt"

# Check if input file exists
if [ ! -f "$INPUT_FILE" ]; then
    echo "Error: $INPUT_FILE not found!"
    exit 1
fi

# Process the file:
# 1. Skip the header line
# 2. Remove duplicate Class IDs (keep first occurrence)
# 3. Sort alphabetically by Class ID
# 4. Add the header back at the top

{
    # Print the header
    head -n 1 "$INPUT_FILE"

    # Process data lines: remove duplicates and sort
    tail -n +2 "$INPUT_FILE" | \
    awk -F'#' '!seen[$1]++' | \
    sort -t'#' -k1,1
} > "$OUTPUT_FILE"

echo "Done! Created $OUTPUT_FILE with unique Class IDs sorted alphabetically."
echo "Total unique courses: $(wc -l < "$OUTPUT_FILE")"
