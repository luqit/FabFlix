import sys

TJsum = 0
TSsum = 0
count = 0

inFile = sys.argv[1]

with open(inFile, 'r') as f:
	for line in f:
		numbers = [int(num) for num in line.split(',')]
		print(numbers)
		if(len(numbers) == 2):
			TSsum += numbers[0]
			TJsum += numbers[1]
			count += 1
if(count > 0):
	print("TJ Average: ", TJsum/count/1000000.0, "ms")
	print("TS Average: ", TSsum/count/1000000.0, "ms")
	print(count)
else:
	print("File is empty")

#empty file only if 'empty' command line argument given
if(len(sys.argv) == 2):
	if(sys.argv[1] == 'empty'):
		open("/p5log/searchTimes.txt", 'w').close() #empty file after calculating averages
		print("The log file was cleared")
