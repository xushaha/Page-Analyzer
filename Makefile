test:
	make -C app test

clean:
	make -C app clean

lint:
	make -C app lint

build:
	make -C app build

install:
	make -C app install

report:
	make -C app report

build-run: build

.PHONY: build

