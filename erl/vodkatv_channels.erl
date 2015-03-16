-module(vodkatv_channels).

-include_lib("eqc/include/eqc.hrl").
-include("measure.hrl").

-compile(export_all).

num_channels(N) ->
    [N + 50].

eval_cmds(N) ->
    SetupCommands = get_setup_commands(),
    RunCommands = get_run_commands(N),
    TearDownCommands = get_teardown_commands(),
    measure_java:run_java_commands(false, 50, SetupCommands,
        lists:flatten(RunCommands), TearDownCommands).

measure_size(N) ->
    N.

get_java_code(Commands) ->
    ["{",
        "try {"
            "VoDKATVChannels v = VoDKATVChannels.getInstance();",
            Commands,
        "} catch(Exception e) {",
            "e.printStackTrace();",
        "}",
        "return null;",
    "}"].

get_run_commands(N) ->
    Commands = get_java_code([
        "v.findChannelsInformation(" ++ integer_to_list(N) ++ ");"
    ]),
    lists:flatten(Commands).

get_setup_commands() ->
    Commands = get_java_code([
        "v.setUp();"
    ]),
    lists:flatten(Commands).

get_teardown_commands() ->
    Commands = get_java_code([
        "v.tearDown();"
    ]),
    lists:flatten(Commands).

global_setup() ->
    ok.

global_teardown() ->
    ok.

measure() ->
    java:set_timeout(infinity),
    ClassPaths = ["../examples/vodkatv/src/", "../examples/vodkatv/vodkatv/"] ++
            filelib:wildcard("../examples/vodkatv/lib/*.jar"),
    Family = #family{initial = 0, grow = fun num_channels/1},
    Axes = #axes{size = fun measure_size/1,
                time = fun eval_cmds/1,
                repeat = 5},
    {Time, _} = timer:tc(measure_java, measure_java,
        [1,  1000, Family, Axes, ClassPaths,
        fun global_setup/0, fun global_teardown/0]),
    Time / 1000000.
