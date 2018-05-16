Date: 14th January, 2018
Author: Patrick Plieschnegger

#Markdown

## Beginning
Even with some formal knowledge in pipes and filters (PnF) architecture creating this rather simple "Iteration" Stream-driven 
API cost me a bit more than 3 days of work.

## Specification
An "Iteration" is a collection driven process that mutates its containing data but not the owning collection.
It could be described as a very light-weight Stream API that focuses on Iterables and lacks more sophisticated tasks 
such as parallelism or bi-directional control of data.

In essence, an Iteration is just an abstraction of a for-each loop and allows to reduce boilerplate code for usual
for-each tasks. Whereas Streams require a Spliterator to be created, an Iteration is content with a sole iterator.

An Iteration only provides methods which do not require a new Collection to be instantiated.
And like Streams, an Iteration is sink driven.

## Implementation
Given the specification (non mutable collection, no parallelism, sink driven) one would think that forwarding data in 
pipes is almost trivial. However, when applying classical PnF the architecture becomes too bloated and 
more difficult to implement without any additional gain.

Most of all, ordinary PnF is concerned about bi-directionality, whereas this approach is purely unidirectional.
In this context, bi-directionality refers to having a pull AND push mechanics to trigger data transformation.
While I considered implementing bi-directionality (because why not) it simply was not worth the additional memory footprint
and increased architecture size. It turned out to be too burdensome for something that is essentially a gimmick.

Because allowing a pushing pipe would mean that you first create an Iteration variable and then call push in a new statement.

## What did I learn?

### Visualizing Data Flow
Most of all: "Visualize the data flow of the application". I was too focused on creating a class diagram, making a 
nice class hierarchy and were aiming to make everything work together perfectly. Granted, it was a learning experience
and at the beginning it was very difficult to actually visualize the end flow of the data.

I simply started making interfaces to distribute the logic without further knowing how a pipe will actually incorporate 
into an Iteration object. Finding out how an Iteration will aggregate a pipe and how Iteration objects are chained 
together should have been the main focus, as the data-flow suggests.

The data-flow itself is actually rather trivial and can easily be visualized as one of the following:
... <- source <- source <- source <- sink 
( ( null | sink ) <- source ) <- sink

Unlike classical PnF there is no need for a "body" pipe that implements both characteristics of a source and sink.
Every Iteration is seen as a source of its own that can be called by a following Iteration and the end will be marked by
a Termination operation (sink). In hindsight, I clearly did overdesign and wanted to combine minimalism with arbitrary beauty.  

### Sometimes, more is more
In terms of prototyping I think it has proven useful to use several interfaces to mark a class with a certain functionality.
Of course, logical core is better distributed over a small selection of classes and associations are better to be kept at a minimum.
However, when it comes to inheriting Interfaces I am more convinced now that it is better to be more generous and distribute your
methods over several smaller interfaces with a meaningful name.

Some interfaces are redundant (like Computation and Termination or Aggregator and Provider) but provide far better readability
and logical structure within the package. 

Especially Aggregator and Provider are crucial and solved to visualize the problem that a Source could only return its 
input values or output values. In the original design, it was implementing Iterator, which was problematic for several reasons.
In hindsight, this is another trivial issue that caused unnecessary headache. 

Lesson Learned: Iterators are great, but they only work for trivial sequences where no mapping of any kind is performed.
In many regards an Iterator is like a limited Supplier, but since every Class can have only be an Iterable of one kind
you run into problems when there are return values of different types in your context.
Again, a seemingly trivial mistake I did not consider and will try to incorporate into my further thinking.
