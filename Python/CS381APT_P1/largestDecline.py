"""
CS381 Advanced Programming Techniques - Project 1
Jason Yeo
"""
from math import sin, cos

# input_file = open('input.dat')
# input_list = [ int(n) for n in input_file.split() ]
with open('input.txt') as file_input:
    for line in file_input:
        input_list = [ int (x) for x in line.split() ]
        print (input_list)
        plotted_list = []
        for i in range(input_list[-1]):
            plotted_list.append(input_list[0]*
                        (sin(input_list[1]*(i+1)+input_list[2])+
                         cos(input_list[3]*(i+1)+input_list[4])+2))
        print(plotted_list)
        maxval, maxdec = 0.0, 0.0
        for i in plotted_list:
            maxdec = max ( maxdec, maxval - i )
            maxval = max ( maxval, i )
        
        file_output = open ('output.txt', 'a')
        file_output.write("%.6f \n" % maxdec)


# print("Type in 6 numbers to find the largest decline:")
# # input_list = []
# for i in range(6):
#     while True:
#         try: 
#             numbers = int( input ( 'Type in number %d:' % ( i+1 ) ) )
#             input_list.append(numbers)
#             break;
#         except ValueError:
#             print("That is not a valid number.")
#     
# print (input_list)
#      
# # print ( 'What do you want the name of your OUTPUT file to be?' )
# # output_file = sys.stdin.readline
# plotted_list = []
# # for i in range(input_list[-1]):
# #     print(i)
# for i in range(input_list[-1]):
#     plotted_list.append(input_list[0]*
#                         (sin(input_list[1]*(i+1)+input_list[2])+
#                          cos(input_list[3]*(i+1)+input_list[4])+2))
# #     print(input_list[0]*(sin(input_list[1]*(i+1)+input_list[2])+cos(input_list[3]*(i+1)+input_list[4])+2))
# 
# print(plotted_list)
# # input_list = [ 42, 1, 23, 4, 8, 10 ]
# 
# maxval, maxdec = 0.0, 0.0
# for i in plotted_list:
#     maxdec = max ( maxdec, maxval - i )
#     maxval = max ( maxval, i )
#     print ( i )
    
# file_output = open('output.dat', 'w')
# file_output.write(maxdec)
# file_output.close()

# with open ('output.dat', 'a') as file_output:
#     file_output.write("%.6f" % maxdec)
# file_output.closed
# 
# print ( "Largest decline is %.6f" % maxdec )






