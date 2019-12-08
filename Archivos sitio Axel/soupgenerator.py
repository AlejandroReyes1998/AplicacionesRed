import random
import string
import copy
#List of words to be written on the soupp
words = ['PYTHON', 'ROBBIE', 'GITHUB', 'BEEF','EMORALES']
#Prints the grid
def print_grid(grid):
	grid_size=15
	for x in range(grid_size):
		print('\t'*5+' '.join(grid[x]))
#Fills grid with random letters
def fill_grid(gridx):
	grid_size=15
	for x in range(grid_size):
		for y in range(grid_size):
			if(gridx[x][y] == '_'):
				gridx[x][y] = random.choice(string.ascii_uppercase)
#Generates soup
def generategrid(words):
	orientations = ['leftright','updown','diagonalup','diagonaldown']
	grid_size=15
	grid = [['_' for _ in range(grid_size)] for _ in range(grid_size)]
	for word in words:
		word_length = len(word)
		placed = False
		while not placed:
			orientation = random.choice(orientations)
			#Sets orientation given by a random number
			if orientation == 'leftright':
				step_x=1
				step_y=0
			if orientation == 'updown':
				step_x = 0
				step_y = 1
			if orientation == 'diagonalup':
				step_x = 1
				step_y = 1
			if orientation == 'diagonaldown':
				step_x = 1
				step_y = -1
			#We generate a random starting point, then we calculate the ending point and if it exceeds the limit, we calculate the number again
			x_position = random.randrange(grid_size)
			y_position = random.randrange(grid_size)

			ending_x = x_position + word_length*step_x
			ending_y = y_position + word_length*step_y

			if ending_x < 0 or ending_x >= grid_size: continue
			if ending_y < 0 or ending_y >= grid_size: continue
			failed = False
			#we set the word on the previously given position
			for i in range(word_length):
				character = word[i]
				new_position_x = x_position + i*step_x
				new_position_y = y_position + i*step_y
				character_at_new_position = grid[new_position_x][new_position_y]
				#if there is some character that could be used to form the word
				if character_at_new_position != '_':
					if character_at_new_position == character:
						continue
					else:
						failed = True
						break
			if failed:
			#We do the process from above again until we can put the word on the grid 
				continue
			else:
				#Everything worked perfectly and the word was placed without problems
				for i in range(word_length):
					character = word[i]
					new_position_x = x_position + i*step_x
					new_position_y = y_position + i*step_y
					grid[new_position_x][new_position_y] = character
					placed = True
	return grid
def main():
	#Puts the words on the soup
	grid=generategrid(words)
	#Copy the solution
	grid_sol=copy.deepcopy(grid)
	#Fill the original soup with random letters
	fill_grid(grid)
	#Prints full soup
	print("------------SOUP-----------")
	print_grid(grid)
	print("------------SOLUTION-----------")
	#Prints only the solution
	print_grid(grid_sol)
main()