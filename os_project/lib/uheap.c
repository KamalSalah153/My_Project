#include <inc/lib.h>

// malloc()
//	This function use FIRST FIT strategy to allocate space in heap
//  with the given size and return void pointer to the start of the allocated space

//	To do this, we need to switch to the kernel, allocate the required space
//	in Page File then switch back to the user again.
//
//	We can use sys_allocateMem(uint32 virtual_address, uint32 size); which
//		switches to the kernel mode, calls allocateMem(struct Env* e, uint32 virtual_address, uint32 size) in
//		"memory_manager.c", then switch back to the user mode here
//	the allocateMem function is empty, make sure to implement it.

//==================================================================================//
//============================ REQUIRED FUNCTIONS ==================================//
//==================================================================================//
uint32 Start=USER_HEAP_START;
uint32 End=USER_HEAP_MAX;
int AllocatedFrames[(USER_HEAP_MAX-USER_HEAP_START)/PAGE_SIZE]={0};
int arrayIndex=0;
struct AllocatedFramesInfo
{
	uint32 va;
	int NumberOfFrames;
};
struct AllocatedFramesInfo array[1000];

void* malloc(uint32 size)
{

	int AllFrames=(End-Start)/(PAGE_SIZE);
	int AvailableFramesCounter=0,FramesNeeded=(size+PAGE_SIZE-1)/PAGE_SIZE;
	int MinFrames=AllFrames;
	uint32 VirtualAddressLoop=Start,ReturnVA=-1;

	for (int i=0;i<AllFrames;i++)
	{
		if (AllocatedFrames[i]==1)
		{
			if (AvailableFramesCounter>=FramesNeeded&&AvailableFramesCounter<MinFrames)
			{
				MinFrames=AvailableFramesCounter;
				ReturnVA=VirtualAddressLoop-(AvailableFramesCounter*PAGE_SIZE);
				//cprintf("%d ",AllFrames);
			}
			AvailableFramesCounter=0;
		}
		else
		{
			AvailableFramesCounter++;
		}
		VirtualAddressLoop+=PAGE_SIZE;
	}
	if (AvailableFramesCounter>=FramesNeeded&&AvailableFramesCounter<=MinFrames){
		MinFrames=AvailableFramesCounter;
		ReturnVA=VirtualAddressLoop-(AvailableFramesCounter*PAGE_SIZE);
	}
	//cprintf("%d %d %d",MinFrames,FramesNeeded,AllFrames);
	if (ReturnVA==-1)return NULL;
	sys_allocateMem(ReturnVA,size);
	int counter=(ReturnVA-Start)/PAGE_SIZE;
	for (int i=0;i<FramesNeeded;i++){
		AllocatedFrames[counter]=1;
		counter++;
		//cprintf("%d ",TmpAddress-Start);
	}
	array[arrayIndex].va=ReturnVA;
	array[arrayIndex].NumberOfFrames=FramesNeeded;
	arrayIndex++;
	//This function should find the space of the required range
	//using the BEST FIT strategy

	//refer to the project presentation and documentation for details
	return (void*)ReturnVA;
}

// free():
//	This function frees the allocation of the given virtual_address
//	To do this, we need to switch to the kernel, free the pages AND "EMPTY" PAGE TABLES
//	from page file and main memory then switch back to the user again.
//
//	We can use sys_freeMem(uint32 virtual_address, uint32 size); which
//		switches to the kernel mode, calls freeMem(struct Env* e, uint32 virtual_address, uint32 size) in
//		"memory_manager.c", then switch back to the user mode here
//	the freeMem function is empty, make sure to implement it.

void free(void* virtual_address)
{

		uint32 size;
		uint32 va,m;
		for(int i=0; i<arrayIndex; i++)
		{
			if(array[i].va == (uint32)virtual_address)

			{
				va =array[i].va;
				m=array[i].NumberOfFrames;
				int start_index =(va-USER_HEAP_START)/PAGE_SIZE;
							for (int i = 0 ; i <m;i++)
							{

								AllocatedFrames[start_index]=0;
								start_index++;
							}

				size = array[i].NumberOfFrames*PAGE_SIZE;
				sys_freeMem((uint32)virtual_address,size);
				array[i].va=0;
				array[i].NumberOfFrames=0;
				break;

			}
		}












}

//==================================================================================//
//================================ OTHER FUNCTIONS =================================//
//==================================================================================//

void* smalloc(char *sharedVarName, uint32 size, uint8 isWritable)
{
	panic("this function is not required...!!");
	return 0;
}

void* sget(int32 ownerEnvID, char *sharedVarName)
{
	panic("this function is not required...!!");
	return 0;
}

void sfree(void* virtual_address)
{
	panic("this function is not required...!!");
}

void *realloc(void *virtual_address, uint32 new_size)
{
	panic("this function is not required...!!");
	return 0;
}

void expand(uint32 newSize)
{
	panic("this function is not required...!!");
}
void shrink(uint32 newSize)
{
	panic("this function is not required...!!");
}

void freeHeap(void* virtual_address)
{
	panic("this function is not required...!!");
}
