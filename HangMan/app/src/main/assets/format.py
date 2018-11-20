file = open("E:\\AndroidStudioProjects\\HangMan\\app\\src\\main\\assets\\common_magoosh.txt","r")
fp = open("E:\\AndroidStudioProjects\\HangMan\\app\\src\\main\\assets\\common_magoosh_ps.txt","w")
fm = open("E:\\AndroidStudioProjects\\HangMan\\app\\src\\main\\assets\\common_magoosh_meaning.txt","w")


for i in file:
	a = i.split()
	fp.write(a[0]+"\n")
	del a[0:1]
	fm.write(' '.join(a)+"\n")