from pathlib import Path
import subprocess
import shlex
import os


#################
# Environment variables
################
CACHE_PATH = '/opt/cache'
ASDF_CACHE_PATH = '/opt/cache/asdf'
JENKINS_CACHE_PATH = '/opt/cache/jenkins'
CLEAR_CACHE_PATHS = 'CLEAR_CACHE_PATHS'

#################
# Helper functions
################
def _create_paths(path: str):
    Path(path).mkdir(parents=True, exist_ok=True)
    print(f'Cache workdir {path} created')

def _parse_env_var_as_bool(env_name: str):
    env = os.getenv(env_name)
    if (
        env != None
        and
        type(env) == str
        and
        env.lower() in ['yes', 'y', 'true', 't']
    ):
        return True
    return False

def _clear_cache_paths(paths: [str]):
    if _parse_env_var_as_bool(CLEAR_CACHE_PATHS) == True:
        for path in paths:
            try:
                bash_path: str = shlex.quote(path)
                subprocess.run(['bash', '-c', f'rm -rf {path}/*'], check=True)
                print(f'Cache path {path} files removed')
            except subprocess.CalledProcessError as exc:
                print(f'Something goes wrong clearing cache callint to bash: \n{exc}')
            except Exception as exc:
                print(f'Something goes wrong clearing cache: \n{exc}')

#################
# Main thred
################
def entrypoint():
    _create_paths(CACHE_PATH)
    _create_paths(ASDF_CACHE_PATH)
    _create_paths(JENKINS_CACHE_PATH)
    _clear_cache_paths([ASDF_CACHE_PATH, JENKINS_CACHE_PATH])

if __name__ == "__main__":
    entrypoint()
