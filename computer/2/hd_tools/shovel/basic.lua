
os.loadAPI("/global_utils/dig.lua")

print("Please enter the length to proceed.")
local total = tonumber(io.read())

print("If the floor is empty, would you fill it with soil?")
local underAccept = io.read()
local bol = (underAccept == "yes" or underAccept == "true")

print("\nThe length to proceed is " .. total)
print((bol and "I'll fill the floor with soil.\n" or "It does not fill the floor.\n"))

dig.FlatDig(total, bol)
