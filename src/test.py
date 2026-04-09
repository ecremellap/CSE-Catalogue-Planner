from fastapi import FastAPI, HTTPException, status
from pydantic import BaseModel
from typing import Optional

# Create the FastAPI app (this is the main interaction point)
app = FastAPI(title="Student Task Manager")

# In-memory "database"
tasks_db = [
    {
        "id": 1,
        "title": "Learn FastAPI",
        "desc": "Finish the workshop",
        "completed": False
    }
]


class Task(BaseModel):
    id: int
    title: str
    desc: Optional[str] = None
    completed: bool = False


@app.get("/", tags=["Root"])
def read_root():
    return {"message": "Welcome to the Student Task API!"}


@app.get("/tasks", status_code=status.HTTP_200_OK)
def get_tasks(completed: Optional[bool] = None):
    if completed is not None:
        return [t for t in tasks_db if t["completed"] == completed]
    return tasks_db


@app.post("/tasks", status_code=status.HTTP_201_CREATED)
def create_task(new_task: Task):
    if any(t["id"] == new_task.id for t in tasks_db):
        raise HTTPException(
            status_code=400, 
            detail="Task id already exists"
        )
    
    tasks_db.append(new_task.dict())        # or new_task.model_dump() in newer Pydantic v2
    return {"message": "Task created successfully", "task": new_task}


@app.get("/tasks/{task_id}")
def get_task(task_id: int):
    for task in tasks_db:
        if task["id"] == task_id:
            return task
    raise HTTPException(
        status_code=status.HTTP_404_NOT_FOUND,
        detail=f"Task with id {task_id} was not found."
    )


@app.delete("/tasks/{task_id}")
def delete_task(task_id: int):
    global tasks_db
    original_length = len(tasks_db)
    tasks_db = [t for t in tasks_db if t["id"] != task_id]
    
    if len(tasks_db) == original_length:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Task with id {task_id} not found."
        )
    
    return {"message": "Task deleted successfully"}



    """
    In bash
    curl -X 'POST'
        'http://127.0.0.1:8000/tasks' \
        -H 'Content-Type: application/json' \
        -d '{
        "id": 2.
        "title": "Build a FrontEnd",
        "completed": false
        }