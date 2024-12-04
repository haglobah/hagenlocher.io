{
  inputs = {
    nixpkgs.url = "https://flakehub.com/f/DeterminateSystems/nixpkgs-weekly/*.tar.gz";
    flake-parts.url = "github:hercules-ci/flake-parts";
    pre-commit-hooks.url = "github:cachix/git-hooks.nix";
    devshell.url = "github:numtide/devshell";
  };

  outputs = inputs:
    inputs.flake-parts.lib.mkFlake {inherit inputs;} {
      imports = [
        inputs.devshell.flakeModule
        inputs.pre-commit-hooks.flakeModule
      ];
      systems = ["x86_64-linux" "aarch64-linux" "aarch64-darwin" "x86_64-darwin"];
      perSystem = {
        config,
        pkgs,
        ...
      }: {
        pre-commit = {
          settings.hooks = {
            alejandra.enable = true;
            deadnix.enable = true;
          };
        };
        devshells.default = {
          env = [
            # { name = "MY_ENV_VAR"; value = "SOTRUE"; }
          ];
          packages = [
            pkgs.nodejs_22
            pkgs.clojure
            pkgs.zulu
          ];
          commands = [
            {
              name = "create";
              command = "npx create-cljs-project $1";
              help = "Create a new cljs app";
            }
            {
              name = "watch";
              command = "npx shadow-cljs watch $1";
              help = "Run cljs dev server";
            }
            {
              name = "compile";
              command = "npx shadow-cljs compile $1";
              help = "Build a release";
            }
          ];
          devshell = {
            startup = {
              install-npm-dependencies.text = ''npm install'';
              pre-commit.text = config.pre-commit.settings.installationScript;
            };
          };
        };
      };
      flake = {
        # The usual flake attributes can be defined here, including system-
        # agnostic ones like nixosModule and system-enumerating ones, although
        # those are more easily expressed in perSystem.
      };
    };
}
