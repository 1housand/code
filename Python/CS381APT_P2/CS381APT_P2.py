"""
CS381 Advanced Programming Techniques - Project 2
Jason Yeo
"""
from shapely.affinity import translate
from shapely.geometry import Polygon

#Asteroid object with vertices, velocity, and polygon object to calcualte
class Asteroid( object ):
    def __init__( self, spec_list ):
        self.vertices = [ int(x) for x in spec_list[ 1 : spec_list[0]*2+1 ] ]
        self.velocity = [ x for x in spec_list [ spec_list[0]*2+1 : ] ]

        list_iterator = iter( self.vertices )
        self.asteroid = Polygon( zip( list_iterator, list_iterator ) )

#function to calculate time and max area
def findIntersect( ):
    time, max_area, max_time = 0.0, 0.0, 0.0
    while time < 5:
        time+=.001
        temp = asteroid1.asteroid.intersection(asteroid2.asteroid).area
        if temp > max_area:
            max_area = temp
            max_time = time
        asteroid1.asteroid = translate(asteroid1.asteroid, asteroid1.velocity[0]*.001, asteroid1.velocity[1]*.001)
        asteroid2.asteroid = translate(asteroid2.asteroid, asteroid2.velocity[0]*.001, asteroid2.velocity[1]*.001)
    return max_time

# Main
with open('input.txt') as file_input:
    file_lines = file_input.readlines()

file_output = open ('output.txt', 'w')
index = 0
while index < len(file_lines):
    if (file_lines[index] is '\n'):
        index+=1
    spec_list1 = [ int (x) for x in file_lines[index].split() ]

    index+=1
    if (file_lines[index] is '\n'):
        index+=1
    spec_list2 = [ int (x) for x in file_lines[index].split() ]

    asteroid1 = Asteroid(spec_list1)
    asteroid2 = Asteroid(spec_list2)
    file_output = open ('output.txt', 'a')
    file_output.writelines("%s " % item for item in spec_list1)
    file_output.write("\n")
    file_output.writelines("%s " % item for item in spec_list2)
    file_output.write("\n")
    result = findIntersect()
    if result == 0:
        file_output.write("never")
    else:
        file_output.write(str(result))
    file_output.write("\n\n")
    index+=1
file_output.close()
# Main end