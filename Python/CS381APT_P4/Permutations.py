
with open('input.txt') as file_input:
    input_int = int(file_input.readline())

int_list=[]
for x in range(1, input_int+1):
    int_list.append(x)

stack=[]
print_list=[]
i=0
stack.append([int_list, i])
while (stack):
    int_list, i = stack.pop()
    if i == len(int_list)-1:
        print_list.append(list(int_list))
        continue
    for j in range(i, len(int_list)):
        int_list[i], int_list[j] = int_list[j], int_list[i]
        stack.append([list(int_list), i+1])
        int_list[i], int_list[j] = int_list[j], int_list[i]

print('The input number is {}\nThere are {} permutations:'.format(input_int, len(print_list)) )
for x in reversed(print_list):
    print( '{}'.format(x) )

with open('output.txt', 'w') as file_output:
    file_output.write( 'The input number is {}\nThere are {} permutations:\n'.format(input_int, len(print_list)) )
    for x in reversed(print_list):
        file_output.write( '{}\n'.format(x) )