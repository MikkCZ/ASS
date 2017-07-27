def dump_db_items(file,list,rept):
    with open(file,'wt') as write_file:
        for i in range(rept):
            for data in list:
                datum = data[2]
                [rok,mesic,den] = datum.split("-")
                to_write = "{:s} {:s} {:d}.{:d}.{:d}\n".format(data[0],data[1],int(den),int(mesic),int(rok))
                print(to_write)
                write_file.write(to_write)
    

if __name__ == "__main__":
    dump_db_items("jmena_a_narozeni.txt",[('Jan', 'Vorech', '1997-09-27'),('Josef', 'Vorech', '1998-06-10')],2)
    