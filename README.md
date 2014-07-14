ilids
=====

idriver source files
--------------------

This branch contains the device driver(idriver) source files for the ilids 
project.The driver collects data from the energy meter device over an rs485 
wired link.The driver collects values from the device using polling mechanism.
Multiple devices can be connected over this link.The driver updates the 
values obtained from the meter to a central mysql database,from where 
other ilids modules can access the data.
