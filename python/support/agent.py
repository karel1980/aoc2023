from collections import Counter
from langchain.llms import Ollama
from langchain.chat_models import ChatOllama
from langchain.prompts import (
    ChatPromptTemplate,
)
from langchain.schema.output_parser import StrOutputParser
from langchain.agents.agent_types import AgentType
from langchain_experimental.utilities import PythonREPL
from langchain_experimental.agents.agent_toolkits import create_python_agent
from langchain_experimental.tools import PythonREPLTool

def solve_problem(problem):

    agent_executor = create_python_agent(
        llm=Ollama(model="llama2"),
        tool=PythonREPLTool(),
        verbose=True,
        agent_type=AgentType.ZERO_SHOT_REACT_DESCRIPTION,
        agent_executor_kwargs={"handle_parsing_errors": True},
    )

    return agent_executor.run(problem)


def main():
    problem = f"What is the 10th fibonacci number?"
    solution = solve_problem(problem)
    print(solution)


if __name__=="__main__":
    main()
