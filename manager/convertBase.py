input_path = 'balanced.csv'
output_path = 'balanced-converted.csv'

MAX_CLASS = 29

count_classes = {}

with open(input_path, 'r') as infile, open(output_path, 'w') as outfile:
        for line in infile:
            new_line = line.split(",")
            position_to_add_one = int(new_line[8].replace("\n",""))
            if count_classes.get(position_to_add_one) is None:
                  count_classes[position_to_add_one] = 1
            else:
                  count_classes[position_to_add_one] += 1
            line_values = [0] * MAX_CLASS
            line_values[position_to_add_one - 1] = 1
            string_numbers = [str(num) for num in line_values]
            new_line2 = ",".join(string_numbers)
            #line = line.strip()
            #line = line.replace(f"{position_to_add_one}", new_line)
            new_line = new_line[:8] + string_numbers
            new_line = ",".join(new_line) + "\n"
            outfile.write(new_line)

print(count_classes)
max_value = max(count_classes.values())
print(max_value)